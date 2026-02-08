package ge.gtu.springbootdemo.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.Objects;

@Getter
@Setter
@Accessors(chain = true)
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "login", nullable = false, unique = true)
    private String login;
    @Column(name = "firstName")
    private String firstName;
    @Column(name = "middleName")
    private String middleName;
    @Column(name = "lastName")
    private String lastName;
    @Column(name = "age")
    private int age;

    @OneToOne
    @JoinColumn(name = "address_id")
    private Address address;

    @Override
    public boolean equals(Object o){
        if(this == o) return true;
        if(o == null || o.getClass() != getClass()) return false;
        User user = (User) o;
        return login.equals(user.login);
    }
    @Override
    public int hashCode(){
        return Objects.hashCode(login);
    }
    @Override
    public String toString(){
        return "[ID]: " + id + "\n[Login]: " + login;
    }
}
