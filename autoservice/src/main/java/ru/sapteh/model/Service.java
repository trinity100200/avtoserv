package ru.sapteh.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "service")
public class Service {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String title;

    private String cost;

    private int durationInSeconds;

    private String description;

    private String discount;

    private String mainImagePath;

    @OneToMany(mappedBy = "service", cascade = CascadeType.ALL)
    private Set<ClientService> clientServices;


    @Override
    public String toString() {
        return "Service{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", cost='" + cost + '\'' +
                ", durationInSeconds=" + durationInSeconds +
                ", description='" + description + '\'' +
                ", discount='" + discount + '\'' +
                ", mainImagePath='" + mainImagePath + '\'' +
                '}';
    }
}
