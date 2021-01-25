package thb.siprojektanamneseservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Disease {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "disease_Id", unique = true)
    private UUID id;

    @JoinColumn(name = "person_Id")
    @OneToOne(fetch = FetchType.LAZY)
    private Person person;

    private boolean undergoneSurgery;
    private String surgeriesDetails;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "disease_preExistingIllnesses",
            joinColumns = @JoinColumn(name = "disease_Id"),
            inverseJoinColumns = @JoinColumn(name = "preExistingIllnesses_id"))
    private List<Illness> preExistingIllnesses = new ArrayList<>();
}
