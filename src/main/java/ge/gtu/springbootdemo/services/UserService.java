package ge.gtu.springbootdemo.services;

import ge.gtu.springbootdemo.models.Address;
import ge.gtu.springbootdemo.models.User;
import ge.gtu.springbootdemo.repositories.AddressRepository;
import ge.gtu.springbootdemo.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final AddressRepository addressRepository;

    @Autowired
    public UserService(UserRepository userRepository, AddressRepository addressRepository) {
        this.userRepository = userRepository;
        this.addressRepository = addressRepository;
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User findById(int id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User not found: " + id));
    }

    public User findByLogin(String login) {
        return userRepository.findByLogin(login)
                .orElseThrow(() -> new IllegalArgumentException("User not found by login: " + login));
    }

    @Transactional
    public User create(User user) {
        if (user.getLogin() == null || user.getLogin().isBlank()) {
            throw new IllegalArgumentException("login is required");
        }
        if (userRepository.existsByLogin(user.getLogin())) {
            throw new IllegalArgumentException("login already exists: " + user.getLogin());
        }


        Address addr = user.getAddress();
        if (addr != null && addr.getId() == 0) {
            addr = addressRepository.save(addr);
            user.setAddress(addr);
        }

        user.setId(0);
        return userRepository.save(user);
    }

    @Transactional
    public User update(int id, User updated) {
        User existing = findById(id);

        if (updated.getLogin() != null && !updated.getLogin().isBlank()
                && !updated.getLogin().equals(existing.getLogin())) {
            if (userRepository.existsByLogin(updated.getLogin())) {
                throw new IllegalArgumentException("login already exists: " + updated.getLogin());
            }
            existing.setLogin(updated.getLogin());
        }

        existing.setFirstName(updated.getFirstName());
        existing.setMiddleName(updated.getMiddleName());
        existing.setLastName(updated.getLastName());
        existing.setAge(updated.getAge());

        if (updated.getAddress() != null) {
            Address newAddr = updated.getAddress();
            if (newAddr.getId() == 0) {
                newAddr = addressRepository.save(newAddr);
            } else {

                Address finalNewAddr = newAddr;
                Address addrDb = addressRepository.findById(newAddr.getId())
                        .orElseThrow(() -> new IllegalArgumentException("Address not found: " + finalNewAddr.getId()));
                addrDb.setStreet(newAddr.getStreet());
                addrDb.setCity(newAddr.getCity());
                addrDb.setBuilding(newAddr.getBuilding());
                newAddr = addressRepository.save(addrDb);
            }
            existing.setAddress(newAddr);
        }

        return userRepository.save(existing);
    }

    public void delete(int id) {
        userRepository.deleteById(id);
    }
}
