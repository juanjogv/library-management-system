package co.com.juanjogv.lms.domain.repository;

import co.com.juanjogv.lms.domain.model.BorrowingRecord;

import java.util.List;

public interface BorrowingRecordRepository {
    void saveAll(List<BorrowingRecord> borrowingRecords);
}
