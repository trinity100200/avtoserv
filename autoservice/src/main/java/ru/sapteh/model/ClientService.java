package ru.sapteh.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "clientservice")
public class ClientService {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private int id;

    private Date startTime;

    private String comment;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "ClientID")
    private Client client;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "ServiceID")
    private Service service;

    @Override
    public String toString() {
        return "ClientService{" +
                "id=" + id +
                ", startTime=" + startTime +
                ", comment='" + comment + '\'' +
                '}';
    }
}
