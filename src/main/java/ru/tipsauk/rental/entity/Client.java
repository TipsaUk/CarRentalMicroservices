package ru.tipsauk.rental.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "client")
@NoArgsConstructor
@AllArgsConstructor
public class Client {

    public Client(long id) {
        this.id = id;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "surname", nullable = false)
    private String surname;

    @OneToOne(cascade = CascadeType.ALL, mappedBy="client")
    private Document document;

}
