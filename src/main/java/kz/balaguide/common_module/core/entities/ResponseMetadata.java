package kz.balaguide.common_module.core.entities;

import jakarta.persistence.*;
import kz.balaguide.common_module.core.enums.ResponseCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "response_metadata")
public class ResponseMetadata {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "response_code", nullable = false, unique = true)
    @Enumerated(EnumType.STRING)
    private ResponseCode responseCode;

    //TODO DIP-23: Обсудить варианты решения мультиязычности с фронтом, I18 думаю использовать
    private String message;
}


