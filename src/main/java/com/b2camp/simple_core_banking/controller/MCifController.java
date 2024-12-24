package com.b2camp.simple_core_banking.controller;


import com.b2camp.simple_core_banking.dto.MCifResponse;
import com.b2camp.simple_core_banking.dto.MCifRequest;
import com.b2camp.simple_core_banking.repository.MCifRepository;
import com.b2camp.simple_core_banking.service.MCifService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/cif")
public class MCifController {

    private MCifService mCifService;

    @Autowired
    MCifController(MCifService mCifService) {
        this.mCifService = mCifService;
    }

    @GetMapping
    public List<MCifResponse> readMCifList(@Param("customerName") String customerName) {
        return mCifService.reads(customerName);
    }

    @GetMapping("/{cifId}")
    public Optional<MCifResponse> findByCifId(@PathVariable String cifId) {
        return mCifService.findByCifId(cifId);
    }

    @PutMapping("/Edit/{id}")
    public MCifResponse mCifRepository (@PathVariable String id,
                                          @RequestBody MCifRequest request){
        return mCifService.updateCif(id, request);
    }
    @PostMapping
    public MCifResponse createCif (@RequestBody MCifRequest request){
        return mCifService.createCif(request);
    }

    @DeleteMapping("/{cifId}")
    public String delete(@PathVariable String cifId) {
        return mCifService.delete(cifId);
    }

    @PutMapping("/{cifId}")
    public MCifResponse authorization(@PathVariable String cifId) {
        return mCifService.authorization(cifId);
    }
}
