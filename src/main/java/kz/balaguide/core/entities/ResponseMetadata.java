package kz.balaguide.core.entities;

import kz.balaguide.core.enums.ResponseCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
//TODO: Времнно отключены аннотации по созданию сщуности Jpa
//@Entity
//@Table(name = "response_metadata")
public class ResponseMetadata {
    //@Id
    ///@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private ResponseCode responseCode;

    //TODO DIP-23: Обсудить варианты решения мультиязычности с фронтом
    //DELETE: private String messageKazakh;
    //DELETE: private String messageRussian;
    private String message;
}
