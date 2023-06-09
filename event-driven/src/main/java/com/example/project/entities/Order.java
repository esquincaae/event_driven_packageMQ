package com.example.project.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import javax.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String trackingId;
    private String address;
    private String city;
    private String state;
    private String postalCode;
    private String receiver;
    private String clientEmail;
    private String fileUrl;

    @ManyToOne
    @JoinColumn(name = "orderStatus_id")
    @JsonManagedReference
    private OrderStatus status;

    @OneToMany(mappedBy = "order")
    private List<Event> events;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "package_id", referencedColumnName = "id")
    @JsonBackReference
    private Package orderPackage;



}
