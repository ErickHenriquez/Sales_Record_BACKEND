package cl.erick.avla.services;

import java.util.List;
import java.util.Optional;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cl.erick.avla.models.Product;
import cl.erick.avla.models.User;
import cl.erick.avla.models.Record;

import cl.erick.avla.repositories.ProductRepo;
import cl.erick.avla.repositories.RecordRepo;
import cl.erick.avla.repositories.UserRepo;

@Service
public class AppService {
	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private ProductRepo productRepo;
	
	@Autowired
	private RecordRepo recordRepo;
	
	// USER SERVICES
	
	// AUTHENTICATE USER
	public boolean authenticateUser(String email, String password) {
		// first find the user by email
		Optional<User> user = userRepo.findByEmail(email);
		// if we can't find it by email, return false
		if (user == null) {
			return false;
		}
		// if the passwords match, return true, else, return false
		User usuario = user.get();
		return BCrypt.checkpw(password, usuario.getPassword());
	}
	
	// REGISTER USER AND HASH THEIR PASSWORD
	public User registerUser(User user) {
		String hashed = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt());
		user.setPassword(hashed);
		return userRepo.save(user);
	}
	
	// UPDATE USER WITHOUT PASSWORD
	public User updateUser(User user) {
		return userRepo.save(user);
	}
	
	// FIND ALL USERS
	public List<User> findAllUsers(){
		return userRepo.findAll();
	}
	
	// FIND USER BY EMAIL
	public User findUserByEmail(String email) {
		Optional<User> user = userRepo.findByEmail(email);
		if (user.isPresent()) {
			return user.get();
		} 
		else{
			return null;
		}
	}
	
	// FIND USER BY ID
	public User findUserById(Long id) {
		Optional<User> user = userRepo.findById(id);
		if (user.isPresent()) {
			return user.get();
		} 
		else{
			return null;
		}
	}
	
	// DELETE USER
	public void deleteUser(Long id) {
		userRepo.delete(findUserById(id));
	}
	
	// PRODUCT SERVICES
	
	public Product createProduct(Product product) {
		return productRepo.save(product);
	}
	
	public List<Product> findAllProducts(){
		return productRepo.findAll();
	}
	
	public Product findProductById(Long id) {
		Optional<Product> product = productRepo.findById(id);
		if (product.isPresent()) {
			return product.get();
		} 
		else{
			return null;
		}
	}
	
	// DELETE PRODUCT
	public void deleteProduct(Long id) {
		productRepo.delete(findProductById(id));
	}	
	
	
	// RECORD SERVICES
	
	public Record createRecord(Record record) {
		return recordRepo.save(record);
	}
	
	public List<Record> findAllRecords(){
		return recordRepo.findAll();
	}
	
	public List<Record> findRecordsByUserId(User user){
		return recordRepo.findByUser(user);
	}
	
	public List<Record> findRecordsByProductId(Product product){
		return recordRepo.findByProduct(product);
	}


}
