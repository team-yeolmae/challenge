package org.yeolmae.challenge.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.yeolmae.challenge.domain.Level;

import static org.yeolmae.challenge.domain.Level.*;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BadgeService {

    public Level assignBadge(int level) {
        if (level < 3) {
            return BRONZE;
        } else if (level < 6) {
            return SILVER;
        } else if (level < 9) {
            return GOLD;
        } else {
            return DIAMOND;
    }
}

}
