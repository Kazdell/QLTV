package com.practice.QLTV.utils;

import com.practice.QLTV.dto.response.ApiResponse;
import com.practice.QLTV.entity.User;
import com.practice.QLTV.repository.UserRepository;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)

public class UtilsExcel {

    UserRepository userRepository;

    public void generateExcel(HttpServletResponse response, boolean isSearch, String keyword) throws IOException {
        setUpResponse(response);
        List<User> usersList;

        if (isSearch && keyword != null && !keyword.isEmpty()) {
            usersList = userRepository.searchAdvancedUser(PageRequest.of(0, Integer.MAX_VALUE), keyword).getContent();
        } else {
            usersList = userRepository.findAll(PageRequest.of(0, Integer.MAX_VALUE, Sort.by(Sort.Order.asc("username")))).getContent();
        }

        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("Users");
        XSSFRow row = sheet.createRow(0);

        row.createCell(0).setCellValue("Username");
        row.createCell(1).setCellValue("Full Name");
        row.createCell(2).setCellValue("Role");
        row.createCell(3).setCellValue("Email");
        row.createCell(4).setCellValue("Phone Number");
        row.createCell(5).setCellValue("DOB");
        row.createCell(6).setCellValue("isActive");
        row.createCell(7).setCellValue("Address");
        row.createCell(8).setCellValue("Identify Number");


        int dataRowIndex = 1;
        for (User u : usersList) {
            XSSFRow dataRow = sheet.createRow(dataRowIndex);
            dataRow.createCell(0).setCellValue(u.getUsername());
            dataRow.createCell(1).setCellValue(u.getFullName());
            dataRow.createCell(2).setCellValue(u.getRoleGroupId());
            dataRow.createCell(3).setCellValue(u.getEmail());
            dataRow.createCell(4).setCellValue(u.getPhoneNumber());
            dataRow.createCell(5).setCellValue(u.getDob() != null ? u.getDob().toString() : "N/A");
            dataRow.createCell(6).setCellValue(u.getIsActive());
            dataRow.createCell(7).setCellValue(u.getAddress());
            dataRow.createCell(8).setCellValue(u.getIdentityNumber());

            dataRowIndex++;
        }

        ServletOutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        workbook.close();
        outputStream.close();
    }

    public void setUpResponse(HttpServletResponse response) {
        response.setContentType("application/octet-stream");
        String headerKey = "Content-Disposition";
        String headerValue = "attachment;filename=users.xlsx";
        response.setHeader(headerKey, headerValue);
    }

    public ApiResponse<String> getTemporaryFileInServer(MultipartFile file, HttpServletResponse response) {
        String msg = "";
        try {
            String tempDir = System.getProperty("java.io.tmpdir");
            String filePath = tempDir + java.io.File.separator + UUID.randomUUID() + "_" + file.getOriginalFilename();

            java.io.File tempFile = new java.io.File(filePath);
            file.transferTo(tempFile);

            importExcel(response, filePath);

            if (tempFile.exists()) {
                tempFile.delete();
            }
            msg = "Upload success";
        } catch (IOException e) {
            msg = "Error uploading file: " + e.getMessage();
        }
        return ApiResponse.<String>builder()
                .code(200)
                .message(msg)
                .build();
    }

    @Transactional
    public void importExcel(HttpServletResponse response, String filePath) {

        HashMap<String, List<Integer>> emailMap = new HashMap<>();
        HashMap<String, List<Integer>> usernameMap = new HashMap<>();
        HashMap<String, List<Integer>> phoneNumberMap = new HashMap<>();
        HashMap<String, List<Integer>> identityNumberMap = new HashMap<>();

        List<String> existingPhoneNumber = userRepository.findAllPhoneNumber();
        List<String> existingUsername = userRepository.findAllUsername();
        List<String> existingEmails = userRepository.findAllEmail();
        List<String> existingIdentityNumbers = userRepository.findAllIdentityNumbers();

        boolean hasDuplicates = false;

        try (FileInputStream fis = new FileInputStream(filePath)) {
            Workbook workbook = new XSSFWorkbook(fis);
            Sheet sheet = workbook.getSheetAt(0);

            for (Row row : sheet) {
                if (row.getRowNum() == 0) {
                    continue; // Skip header row
                }

                Cell usernameCell = row.getCell(0);
                Cell emailCell = row.getCell(3);

                // Break if both Username and Email are missing (empty row)
                if ((usernameCell == null || usernameCell.getCellType() == CellType.BLANK) &&
                        (emailCell == null || emailCell.getCellType() == CellType.BLANK)) {
                    break;
                }

                // Validate the current row
                String errorMsg = validateRow(
                        row,
                        existingEmails, emailMap,
                        existingUsername, usernameMap,
                        existingPhoneNumber, phoneNumberMap,
                        existingIdentityNumbers, identityNumberMap
                );
                Cell errorCell = row.createCell(9);
                errorCell.setCellValue(errorMsg);
                CellStyle style = row.getSheet().getWorkbook().createCellStyle();
                style.setWrapText(true);
                errorCell.setCellStyle(style);
                if (!errorMsg.isEmpty()) {
                    hasDuplicates = true;
                }
            }
            if (hasDuplicates) {
                exportExcelWithDuplicates(response, workbook);
            } else {
                saveExcelData(sheet);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private String validateRow(Row row, List<String> existingEmails, HashMap<String, List<Integer>> emailMap,
                               List<String> existingUsername, HashMap<String, List<Integer>> usernameMap,
                               List<String> existingPhoneNumber, HashMap<String, List<Integer>> phoneNumberMap,
                               List<String> existingIdentityNumbers, HashMap<String, List<Integer>> identityNumberMap) throws Exception {
        StringBuilder errorMsg = new StringBuilder();

        // Validate username
        Cell usernameCell = row.getCell(0);
        if (usernameCell != null && usernameCell.getCellType() == CellType.STRING) {
            String username = usernameCell.getStringCellValue();
            if (!checkUsername(username)) {
                errorMsg.append("Invalid username\r\n");
            } else {
                if (existingUsername.contains(username)) {
                    errorMsg.append("Username already exists\r\n");
                }
                if (usernameMap.containsKey(username)) {
                    errorMsg.append("Username duplicated at row: ").append(usernameMap.get(username).toString()).append("\r\n");
                } else {
                    usernameMap.put(username, new ArrayList<>());
                }
                usernameMap.get(username).add(row.getRowNum());
            }
        } else {
            errorMsg.append("Username is required\r\n");
        }

        // Validate full name
        Cell fullNameCell = row.getCell(1);
        if (fullNameCell != null && fullNameCell.getCellType() == CellType.STRING) {
            String fullName = fullNameCell.getStringCellValue();
            if (fullName.isEmpty()) {
                errorMsg.append("Full name cannot be empty\r\n");
            }
        } else {
            errorMsg.append("Full name is required\r\n");
        }

        // Validate role group
        Cell roleGroupCell = row.getCell(2); // Assuming role group is in column 2
        if (roleGroupCell != null && roleGroupCell.getCellType() == CellType.NUMERIC) {
            int roleGroupId = (int) roleGroupCell.getNumericCellValue();
            if (!userRepository.existsById(roleGroupId)) {
                errorMsg.append("Invalid Role Group ID: ").append(roleGroupId).append("\r\n");
            }
        } else {
            errorMsg.append("Role Group ID is required and must be numeric\r\n");
        }

        // Validate email
        Cell emailCell = row.getCell(3);
        if (emailCell != null && emailCell.getCellType() == CellType.STRING) {
            String email = emailCell.getStringCellValue();
            if (!checkEmail(email)) {
                errorMsg.append("Invalid email address\r\n");
            } else {
                if (existingEmails.contains(email)) {
                    errorMsg.append("Email already exists\r\n");
                }
                if (emailMap.containsKey(email)) {
                    errorMsg.append("Email duplicated at row: ").append(emailMap.get(email).toString()).append("\r\n");
                } else {
                    emailMap.put(email, new ArrayList<>());
                }
                emailMap.get(email).add(row.getRowNum());
            }
        } else {
            errorMsg.append("Email is required\r\n");
        }

        // Validate phone number
        Cell phoneCell = row.getCell(4);
        if (phoneCell != null && phoneCell.getCellType() == CellType.STRING) {
            String phoneNumber = phoneCell.getStringCellValue();
            if (!checkPhoneNumber(phoneNumber)) {
                errorMsg.append("Invalid phone number\r\n");
            } else {
                if (existingPhoneNumber.contains(phoneNumber)) {
                    errorMsg.append("Phone number already exists\r\n");
                }
                if (phoneNumberMap.containsKey(phoneNumber)) {
                    errorMsg.append("Phone number duplicated at row: ").append(phoneNumberMap.get(phoneNumber).toString()).append("\r\n");
                } else {
                    phoneNumberMap.put(phoneNumber, new ArrayList<>());
                }
                phoneNumberMap.get(phoneNumber).add(row.getRowNum());
            }
        } else {
            errorMsg.append("Phone number is required\r\n");
        }

        // Validate date of birth
        Cell dobCell = row.getCell(5);
        if (dobCell != null && dobCell.getCellType() == CellType.STRING) {
            try {
                String dobStr = dobCell.getStringCellValue();
                new SimpleDateFormat("yyyy-MM-dd").parse(dobStr); // Validate date format
            } catch (Exception e) {
                errorMsg.append("Invalid date of birth format. Use yyyy-MM-dd\r\n");
            }
        } else {
            errorMsg.append("Date of birth is required\r\n");
        }

        // Validate is_active field
//        Cell isActiveCell = row.getCell(6);
//        if (isActiveCell != null && isActiveCell.getCellType() == CellType.STRING) {
//            String isActiveStr = isActiveCell.getStringCellValue().trim();
//            if (!(isActiveStr.equalsIgnoreCase("true") || isActiveStr.equalsIgnoreCase("false"))) {
//                errorMsg.append("is_active must be true or false\r\n");
//            }
//        } else if (isActiveCell != null && isActiveCell.getCellType() == CellType.BOOLEAN) {
//            boolean isActiveValue = isActiveCell.getBooleanCellValue();
//        } else {
//            errorMsg.append("is_active is required\r\n");
//        }


        // Validate is_active field
        Cell isActiveCell = row.getCell(6);
        if (isActiveCell != null) {
            String isActiveStr = null;
            if (isActiveCell.getCellType() == CellType.STRING) {
                isActiveStr = isActiveCell.getStringCellValue().trim().toLowerCase();
            } else if (isActiveCell.getCellType() == CellType.BOOLEAN) {
                isActiveStr = isActiveCell.getBooleanCellValue() ? "true" : "false";
            }

            if (isActiveStr != null && (isActiveStr.equals("true") || isActiveStr.equals("false"))) {
                // Translate "true" -> 1, "false" -> 0
                int isActiveValue = isActiveStr.equals("true") ? 1 : 0;
                row.createCell(10).setCellValue(isActiveValue); // Optional: Add this as a debug column
            } else {
                errorMsg.append("is_active must be true or false\r\n");
            }
        } else {
            errorMsg.append("is_active is required\r\n");
        }



        // Validate address
        Cell addressCell = row.getCell(7);
        if (isActiveCell != null) {
            if (isActiveCell.getCellType() == CellType.BOOLEAN) {
                boolean isActive = isActiveCell.getBooleanCellValue();
            } else if (isActiveCell.getCellType() == CellType.STRING) {
                String isActiveStr = isActiveCell.getStringCellValue();
                if (!isActiveStr.equalsIgnoreCase("true") && !isActiveStr.equalsIgnoreCase("false")) {
                    errorMsg.append("is_active must be true or false\r\n");
                }
            } else {
                errorMsg.append("Invalid is_active value\r\n");
            }
        } else {
            errorMsg.append("is_active is required\r\n");
        }

        // Validate identify number
        Cell identifyNumberCell = row.getCell(8);
        if (identifyNumberCell != null && identifyNumberCell.getCellType() == CellType.STRING) {
            String identifyNumber = identifyNumberCell.getStringCellValue();
            if (identifyNumber.isEmpty() || identifyNumber.length() < 6) {
                errorMsg.append("Invalid identify number\r\n");
            } else {
                if (existingIdentityNumbers.contains(identifyNumber)) {
                    errorMsg.append("Identify number already exists\r\n");
                }
                if (identityNumberMap.containsKey(identifyNumber)) {
                    errorMsg.append("Identify number duplicated at row: ").append(identityNumberMap.get(identifyNumber).toString()).append("\r\n");
                } else {
                    identityNumberMap.put(identifyNumber, new ArrayList<>());
                }
                identityNumberMap.get(identifyNumber).add(row.getRowNum());
            }
        } else {
            errorMsg.append("Identify number is required\r\n");
        }

        return errorMsg.toString();
    }


    private void exportExcelWithDuplicates(HttpServletResponse response, Workbook workbook) throws IOException {
        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "attachment; filename=duplicatedRowExcel.xlsx");

        ServletOutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        workbook.close();
        outputStream.close();
    }

    @Transactional
    public void saveExcelData(Sheet sheet) throws Exception {
        List<User> usersToSave = new ArrayList<>();
        for (Row r : sheet) {
            User user = new User();
            if (r.getRowNum() == 0) {
                continue;
            }

            Cell usernameCell = r.getCell(0);
            if (usernameCell == null || usernameCell.getCellType() == CellType.BLANK) {
                break;
            }
            if (usernameCell.getCellType() == CellType.STRING) {
                user.setUsername(usernameCell.getStringCellValue());
            } else {
                throw new Exception("Username is required at row " + (r.getRowNum() + 1));
            }

            Cell fullNameCell = r.getCell(1);
            if (fullNameCell != null && fullNameCell.getCellType() == CellType.STRING) {
                user.setFullName(fullNameCell.getStringCellValue());
            } else {
                throw new Exception("Full name is required at row " + (r.getRowNum() + 1));
            }

            Cell roleGroupCell = r.getCell(2);
            if (roleGroupCell != null && roleGroupCell.getCellType() == CellType.NUMERIC) {
                user.setRoleGroupId((int) roleGroupCell.getNumericCellValue());
            } else {
                throw new Exception("Role group ID is required at row " + (r.getRowNum() + 1));
            }

            Cell emailCell = r.getCell(3);
            if (emailCell != null && emailCell.getCellType() == CellType.STRING) {
                user.setEmail(emailCell.getStringCellValue());
            } else {
                throw new Exception("Email is required at row " + (r.getRowNum() + 1));
            }

            Cell phoneNumberCell = r.getCell(4);
            if (phoneNumberCell != null && phoneNumberCell.getCellType() == CellType.STRING) {
                user.setPhoneNumber(phoneNumberCell.getStringCellValue());
            }

            Cell dobCell = r.getCell(5);
            if (dobCell != null && dobCell.getCellType() == CellType.STRING) {
                try {
                    user.setDob(new SimpleDateFormat("yyyy-MM-dd").parse(dobCell.getStringCellValue()));
                } catch (Exception e) {
                    throw new Exception("Invalid date format for DOB at row " + (r.getRowNum() + 1));
                }
            } else {
                throw new Exception("DOB is required at row " + (r.getRowNum() + 1));
            }

            Cell isActiveCell = r.getCell(6);
            if (isActiveCell != null) {
                if (isActiveCell.getCellType() == CellType.BOOLEAN) {
                    user.setIsActive(isActiveCell.getBooleanCellValue());
                } else if (isActiveCell.getCellType() == CellType.STRING) {
                    user.setIsActive(isActiveCell.getStringCellValue().trim().equalsIgnoreCase("true"));
                } else if (isActiveCell.getCellType() == CellType.NUMERIC) {
                    user.setIsActive(isActiveCell.getNumericCellValue() == 1); // 1 = true
                } else {
                    user.setIsActive(false);
                }
            } else {
                user.setIsActive(false);
            }
            Cell addressCell = r.getCell(7);
            if (addressCell != null && addressCell.getCellType() == CellType.STRING) {
                user.setAddress(addressCell.getStringCellValue());
            }
            Cell identityNumberCell = r.getCell(8);
            if (identityNumberCell != null && identityNumberCell.getCellType() == CellType.STRING) {
                user.setIdentityNumber(identityNumberCell.getStringCellValue());
            }
            user.setPassword("T12345678");
            user.setIsActive(true);
            user.setIsDeleted(false);

            usersToSave.add(user);
        }

        System.out.println("Users to save: " + usersToSave.size());
        userRepository.saveAll(usersToSave);
        userRepository.flush();
    }






    private boolean checkUsername(String username) {
        return username.length() >= 4 && username.length() <= 30 && username.matches("^[a-zA-Z0-9]+$");
    }

    private boolean checkPhoneNumber(String phoneNumber) {
        return phoneNumber.matches("^[0-9]{10,15}$");
    }

    private boolean checkEmail(String email) throws Exception {
        return email.matches("^[A-Za-z0-9+_.-]+@(.+)$");
    }
}

