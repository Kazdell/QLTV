package com.practice.QLTV.service.impl;

import com.practice.QLTV.dto.BorrowDTO;
import com.practice.QLTV.dto.BorrowDetailDTO;
import com.practice.QLTV.dto.response.ApiResponse;
import com.practice.QLTV.entity.Borrow;
import com.practice.QLTV.entity.BorrowDetail;
import com.practice.QLTV.exception.AppException;
import com.practice.QLTV.exception.ErrorCode;
import com.practice.QLTV.repository.BorrowRepository;
import com.practice.QLTV.repository.BookRepository;
import com.practice.QLTV.repository.UserRepository;
import com.practice.QLTV.service.BorrowService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BorrowServiceImp implements BorrowService {

    private final BorrowRepository borrowRepository;
    private final UserRepository userRepository;
    private final BookRepository bookRepository;

    @Transactional
    @Override
    public ApiResponse<BorrowDTO> createBorrow(BorrowDTO borrowDTO) {
        validateBorrowDTO(borrowDTO);
        Borrow borrow = Borrow.builder()
                .userId(borrowDTO.getUserId())
                .borrowDate(borrowDTO.getBorrowDate())
                .returnDate(borrowDTO.getReturnDate())
                .status(borrowDTO.getStatus())
                .totalQuantity(borrowDTO.getTotalQuantity())
                .build();
        borrow = borrowRepository.save(borrow);

        for (BorrowDetailDTO detailDTO : borrowDTO.getBorrowDetails()) {
            borrowRepository.insertBorrowDetail(borrow.getId(), detailDTO.getBookId(), detailDTO.getQuantity());
        }

        BorrowDTO result = toBorrowDTO(borrow);
        return ApiResponse.<BorrowDTO>builder()
                .code(ErrorCode.USER_CREATED_SUCCESSFULLY.getCode()) // 1000
                .message("Borrow created successfully")
                .result(result)
                .build();
    }

    @Transactional
    @Override
    public ApiResponse<BorrowDTO> updateBorrow(Integer borrowId, BorrowDTO borrowDTO) {
        validateBorrowDTO(borrowDTO);
        Borrow borrow = borrowRepository.findById(borrowId)
                .orElseThrow(() -> new AppException(ErrorCode.RESOURCE_NOT_FOUND));
        borrow.setReturnDate(borrowDTO.getReturnDate());
        borrow.setStatus(borrowDTO.getStatus());
        borrow = borrowRepository.save(borrow);
        BorrowDTO result = toBorrowDTO(borrow);
        return ApiResponse.<BorrowDTO>builder()
                .code(ErrorCode.USER_UPDATED_SUCCESSFULLY.getCode()) // 1000
                .message("Borrow updated successfully")
                .result(result)
                .build();
    }

    @Transactional
    @Override
    public ApiResponse<Void> deleteBorrow(Integer borrowId) {
        Borrow borrow = borrowRepository.findById(borrowId)
                .orElseThrow(() -> new AppException(ErrorCode.RESOURCE_NOT_FOUND));
        borrowRepository.delete(borrow);
        return ApiResponse.<Void>builder()
                .code(ErrorCode.OPERATION_SUCCESSFUL.getCode()) // 1000
                .message("Borrow deleted successfully")
                .result(null)
                .build();
    }

    @Transactional
    @Override
    public ApiResponse<BorrowDTO> returnBorrow(Integer borrowId) {
        Borrow borrow = borrowRepository.findById(borrowId)
                .orElseThrow(() -> new AppException(ErrorCode.RESOURCE_NOT_FOUND));
        borrow.setStatus("COMPLETED");
        borrow = borrowRepository.save(borrow);
        BorrowDTO result = toBorrowDTO(borrow);
        return ApiResponse.<BorrowDTO>builder()
                .code(ErrorCode.OPERATION_SUCCESSFUL.getCode()) // 1000
                .message("Borrow returned successfully")
                .result(result)
                .build();
    }

    @Override
    public ApiResponse<List<BorrowDTO>> getAllBorrows() {
        List<BorrowDTO> borrows = borrowRepository.findAll().stream()
                .map(this::toBorrowDTO)
                .collect(Collectors.toList());
        return ApiResponse.<List<BorrowDTO>>builder()
                .code(ErrorCode.USER_RETRIEVED_SUCCESSFULLY.getCode()) // 1000
                .message(ErrorCode.USER_RETRIEVED_SUCCESSFULLY.getMessage())
                .result(borrows)
                .build();
    }

    @Override
    public ApiResponse<BorrowDTO> getBorrowById(Integer borrowId) {
        Borrow borrow = borrowRepository.findById(borrowId)
                .orElseThrow(() -> new AppException(ErrorCode.RESOURCE_NOT_FOUND));
        BorrowDTO result = toBorrowDTO(borrow);
        return ApiResponse.<BorrowDTO>builder()
                .code(ErrorCode.USER_RETRIEVED_SUCCESSFULLY.getCode()) // 1000
                .message(ErrorCode.USER_RETRIEVED_SUCCESSFULLY.getMessage())
                .result(result)
                .build();
    }

    @Override
    public ApiResponse<List<BorrowDTO>> getBorrowsByUserId(Integer userId) {
        if (!userRepository.existsById(userId)) {
            throw new AppException(ErrorCode.USER_NOT_EXISTED);
        }
        List<BorrowDTO> borrows = borrowRepository.findByUserId(userId).stream()
                .map(this::toBorrowDTO)
                .collect(Collectors.toList());
        return ApiResponse.<List<BorrowDTO>>builder()
                .code(ErrorCode.USER_RETRIEVED_SUCCESSFULLY.getCode()) // 1000
                .message(ErrorCode.USER_RETRIEVED_SUCCESSFULLY.getMessage())
                .result(borrows)
                .build();
    }

    private BorrowDTO toBorrowDTO(Borrow borrow) {
        List<BorrowDetail> details = borrowRepository.findBorrowDetailsByBorrowId(borrow.getId());
        List<BorrowDetailDTO> detailDTOs = details.stream()
                .map(d -> {
                    BorrowDetailDTO dto = new BorrowDetailDTO();
                    dto.setBookId(d.getBookId());
                    dto.setQuantity(d.getQuantity());
                    return dto;
                })
                .collect(Collectors.toList());
        BorrowDTO dto = new BorrowDTO();
        dto.setUserId(borrow.getUserId());
        dto.setBorrowDate(borrow.getBorrowDate());
        dto.setReturnDate(borrow.getReturnDate());
        dto.setStatus(borrow.getStatus());
        dto.setTotalQuantity(borrow.getTotalQuantity());
        dto.setBorrowDetails(detailDTOs);
        return dto;
    }

    private void validateBorrowDTO(BorrowDTO borrowDTO) {
        if (!userRepository.existsById(borrowDTO.getUserId())) {
            throw new AppException(ErrorCode.USER_NOT_EXISTED);
        }
        for (BorrowDetailDTO detail : borrowDTO.getBorrowDetails()) {
            if (!bookRepository.existsById(detail.getBookId())) {
                throw new AppException(ErrorCode.RESOURCE_NOT_FOUND, "Book ID " + detail.getBookId() + " does not exist");
            }
        }
    }
}