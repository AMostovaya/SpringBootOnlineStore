package ru.geekbrains.repo;

import org.springframework.data.jpa.domain.Specification;
import ru.geekbrains.model.User;

public final class UserSpecification {

    public static Specification<User> trueLiteral() {
        return (root, query, builder)->builder.isTrue(builder.literal(true));
    }

    public static Specification<User> ageGreaterThanOEqual(Integer age) {
        return (root, query, builder) -> builder.greaterThanOrEqualTo(root.get("age"),age);
    }

    public static Specification<User> ageLessThanOEqual(Integer age) {
        return (root, query, builder) -> builder.lessThanOrEqualTo(root.get("age"),age);
    }

    public static Specification<User> findByName(String name) {
        return (root, query, builder) -> builder.like(root.get("name"),"%" + name + "%");
    }

}
