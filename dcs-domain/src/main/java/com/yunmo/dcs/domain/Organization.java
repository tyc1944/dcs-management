package com.yunmo.dcs.domain;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.yunmo.domain.common.Audited;
import com.yunmo.generator.annotation.AutoValueDTO;
import com.yunmo.generator.annotation.ValueField;
import io.genrpc.annotation.ProtoMessage;
import lombok.*;
import lombok.experimental.Accessors;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import java.time.Instant;
import java.util.List;

@Data
@Entity
@Builder
@NoArgsConstructor
@ProtoMessage
@AutoValueDTO
@AllArgsConstructor
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
//@org.hibernate.annotations.Table(appliesTo = "organization", comment ="null")
//@Where(clause = "deleted = 0")
public class Organization  extends Audited {
    @Id
    @GenericGenerator(name = "sequence_id", strategy = "com.yunmo.id.HibernateIdentifierGenerator")
    @GeneratedValue(generator = "sequence_id")
    private Long id;

    @ValueField(value = {"Create", "Rename"})
    @NotNull
    private String name;

//    @JsonSerialize(using = LongToStringSerializer.class)
//    private Long parentId;
//
//    //	private String no;
//    private Instant opDate;
//
//    @Transient
//    private List<Organization> children;
//
//    @Transient
//    private List<OrganizationPosition> organizationPositions;
//
//    @Transient
//    private int nestStaffsCount;
//
//    private boolean deleted;
}
