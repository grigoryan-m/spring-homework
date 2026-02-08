package ge.gtu.springbootdemo.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

import java.util.Objects;

@Entity
@Data
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String street;
    private String city;
    private String building;

    @Override
    public boolean equals(Object o){
        if(this == o) return true;
        if(o == null || o.getClass() != getClass()) return false;
        Address address = (Address) o;
        return street.equals(address.street);
    }

    @Override
    public int hashCode(){
        return Objects.hash(street, city, building);
    }

    @Override
    public String toString(){
        return "[ID]: " + id + "\n[STREET]: " + street + "\n[BUILDING]: " + building;
    }
}
