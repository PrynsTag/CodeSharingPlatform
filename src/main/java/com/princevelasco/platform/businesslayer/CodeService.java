package com.princevelasco.platform.businesslayer;

import com.princevelasco.platform.persistencelayer.CodeRepository;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CodeService {
    private final CodeRepository codeRepository;

    public CodeService(CodeRepository codeRepository) {
        this.codeRepository = codeRepository;
    }

    public List<Code> getTop10Latest() {
        return codeRepository.findTop10ByIsTimeRestrictedFalseAndIsViewRestrictedFalseOrderByDateDesc();
    }

    public Optional<Code> getById(UUID id) {
        codeRepository.findById(id).ifPresent(code -> {
            if (code.isViewRestricted) {
                code.setViews(code.getViews() - 1);
                if (code.getViews() <= -1) {
                    code.setIsToBeDeleted(true);
                }
            }

            if (code.isTimeRestricted) {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
                LocalDateTime codeDate = LocalDateTime.parse(code.getDate(), formatter);

                long duration = Duration.between(codeDate, LocalDateTime.now()).getSeconds();
                code.setTime(code.getTime() - duration);

                if (code.getTime() <= -1) {
                    code.setIsToBeDeleted(true);
                }
            }

            if (code.isToBeDeleted) {
                codeRepository.delete(code);
            } else {
                codeRepository.save(code);
            }
        });

        return codeRepository.findById(id);
    }

    public Code saveCode(Code code) {
        code.setDate(LocalDateTime.now());

        if (code.getTime() > 0) {
            code.setIsTimeRestricted(true);
        }
        if (code.getViews() > 0) {
            code.setIsViewRestricted(true);
        }

        return codeRepository.save(code);
    }

    public void deleteAll() {
        codeRepository.deleteAll();
    }

    public void deleteById(UUID id) {
        codeRepository.deleteById(id);
    }
}
