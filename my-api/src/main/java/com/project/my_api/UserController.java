package com.project.my_api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;

import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RestController 
@RequestMapping("/user")
public class UserController {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;

    public UserController(UserRepository repository, PasswordEncoder passwordEncoder){
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping 
    public List<User> listUser(){
        return repository.findAll();
    }

    @PostMapping 
    public ResponseEntity<User> createUser(@Valid @RequestBody User novoUser){
        String encryptedPassword = passwordEncoder.encode(novoUser.getPassword());
        novoUser.setPassword(encryptedPassword);

        User savedUser = repository.save(novoUser);
        return ResponseEntity.ok(savedUser);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<User> atualizarUsuario(@PathVariable Integer id, @Valid @RequestBody User dadosAtualizados) {
        
        return repository.findById(id)
                .<ResponseEntity<User>>map(usuarioExistente -> {
                    usuarioExistente.setNome(dadosAtualizados.getNome());
                    usuarioExistente.setEmail(dadosAtualizados.getEmail());
                    
                    User usuarioSalvo = repository.save(usuarioExistente);
                    return ResponseEntity.ok(usuarioSalvo);
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarUsuario(@PathVariable Integer id) {
        
        if (!repository.existsById(id)) {
            return ResponseEntity.notFound().build(); 
        }
        
        repository.deleteById(id);
        
        return ResponseEntity.noContent().build();
    }
}
