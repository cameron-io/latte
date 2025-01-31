package io.netstacker.latte.domain.specifications;

import org.springframework.data.jpa.domain.Specification;

import io.netstacker.latte.domain.models.Account;
import io.netstacker.latte.domain.models.Profile;
import io.netstacker.latte.domain.models.Profile_;

public class ProfileSpecifications {
    public static Specification<Profile> profileByAccountId(Account account) {
        return (root, query, builder) -> builder.equal(
                root.get(Profile_.ACCOUNT),
                account);
    }
}
