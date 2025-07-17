package co.com.juanjogv.lms.domain.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.util.UUID;

@Entity
@Table(name = "borrowing_record")
public class BorrowingRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

}
