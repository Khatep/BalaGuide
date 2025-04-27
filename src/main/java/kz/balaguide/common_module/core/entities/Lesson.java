package kz.balaguide.common_module.core.entities;

import com.vladmihalcea.hibernate.type.json.JsonType;
import jakarta.persistence.*;
import org.hibernate.annotations.Type;

import java.util.*;

@Entity
@Table(name = "lesson")
public class Lesson {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID uuid;
    @Column(columnDefinition = "jsonb")
    @Type(JsonType.class)
    Map<String, String> content = new HashMap<>();

}
