package com.princevelasco.platform.presentation;

import com.princevelasco.platform.businesslayer.Code;
import com.princevelasco.platform.businesslayer.CodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
public class APIController {
    @Autowired
    CodeService codeService;

    @GetMapping("api/code/{id}")
    public ResponseEntity<Optional<Code>> getCode(@PathVariable UUID id) {
        Optional<Code> code = codeService.getById(id);

        return code.isPresent() ? ResponseEntity.ok(code) : ResponseEntity.notFound().build();
    }


    @PostMapping("api/code/delete")
    public ResponseEntity<Code> deleteCode() {
        codeService.deleteAll();

        return ResponseEntity.noContent().build();
    }

    @PostMapping("api/code/delete/{id}")
    public ResponseEntity<Code> deleteCode(@PathVariable UUID id) {
        codeService.deleteById(id);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("api/code/latest")
    public List<Code> getLatestCode() {
        return codeService.getTop10Latest();
    }

    @PostMapping("api/code/new")
    public ResponseEntity<Object> newCode(@RequestBody Code codeSnippet) {
        Code code = codeService.saveCode(codeSnippet);

        return ResponseEntity.ok(String.format("{ \"id\" : \"%s\" }", code.getId()));
    }
}
