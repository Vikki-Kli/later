package org.example.item;

import jakarta.persistence.*;
import lombok.*;
import org.example.user.User;

@Getter
@Setter
@ToString
@NoArgsConstructor
@Entity
@Table(name="items")
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name="user_id")
    private User owner;
    private String url;
    private String description;
}
