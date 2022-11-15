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
import javax.validation.constraints.NotNull;
import java.time.Instant;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@AutoValueDTO
@ProtoMessage
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@org.hibernate.annotations.Table(appliesTo = "member", comment ="null")
@SQLDelete(sql = "update member set deleted = 1 where id = ?")
@Where(clause = "deleted = 0")
public class Member  extends Audited {
//	private static final String CREATE_STAFF = "OfCreateStaff";

    @Id
    @GenericGenerator(name = "sequence_id", strategy = "com.yunmo.id.HibernateIdentifierGenerator")
    @GeneratedValue(generator = "sequence_id")
    private long id;

    @NotNull
    private Long organizationId;

    @NotNull
    private Long userAccountId;
}
