package com.b2camp.simple_core_banking.controller;
import com.b2camp.simple_core_banking.entity.MCif;
import com.b2camp.simple_core_banking.repository.MCifRepository;
import com.b2camp.simple_core_banking.service.MCifService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/request")
public class MCifController {
    @Autowired
    private MCifService mCifService;

    @Autowired
    private MCifRepository mCifRepository;

    MCifController(MCifService mCifService) {
        this.mCifService = mCifService;
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateCif(@PathVariable String id, @RequestBody MCif mCifEntity) {
        MCif existingUser = mCifRepository.findById(id).orElse(null);
        if (existingUser != null) {
            existingUser.setCustomerName(mCifEntity.getCustomerName());
            existingUser.setEmail(mCifEntity.getEmail());
            mCifRepository.save(existingUser);
            return ResponseEntity.ok(existingUser);
        }
        return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);

    }


}
