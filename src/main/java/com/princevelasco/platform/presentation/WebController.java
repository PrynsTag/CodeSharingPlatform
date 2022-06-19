package com.princevelasco.platform.presentation;

import com.princevelasco.platform.businesslayer.Code;
import com.princevelasco.platform.businesslayer.CodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Controller
public class WebController {
    @Autowired
    CodeService codeService;

    @GetMapping("/code/{id}")
    public String code(@PathVariable UUID id, Model model) {
        Optional<Code> code = codeService.getById(id);
        if (code.isPresent()) {
            model.addAttribute("title", "Code");
            model.addAttribute("code", List.of(code.get()));

            return "html/code";
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "entity not found");
        }
    }

    @GetMapping(value = "/code/new")
    public String newCode() {
        return "html/new_code";
    }

    @GetMapping("code/latest")
    public String latestSnippet(Model model) {
        model.addAttribute("title", "Latest");
        model.addAttribute("code", codeService.getTop10Latest());

        return "html/code";
    }
}
