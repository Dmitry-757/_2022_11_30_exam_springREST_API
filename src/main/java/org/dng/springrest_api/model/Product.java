package org.dng.springrest_api.model;


import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "product_table")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Basic
    @Column(nullable = false, length = 100)
    private String name;

    @Basic
    @Column(nullable = false)
    private double cost;
    @Basic
    @Column(nullable = false)
    private boolean available;

    public Product() {
    }

    public Product(String name, double cost, boolean available) {
        this.name = name;
        this.cost = cost;
        this.available = available;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Double.compare(product.cost, cost) == 0 && available == product.available && name.equals(product.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, cost, available);
    }
}
