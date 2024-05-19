package ru.tipsauk.rental.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Entity
@Table(name = "document")
@NoArgsConstructor
@AllArgsConstructor
public class Document {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    // TODO проверить правильность указания JoinColumn
    @OneToOne
    @JoinColumn(name = "client_id", referencedColumnName = "id", unique = true, nullable = false)
    private Client client;

    @Column(name = "series", nullable = false)
    private String series;

    @Column(name = "number", nullable = false)
    private String number;

    @Column(name = "valid_date", nullable = false)
    private Date validDate;

}
