package com.mycompany.app.transaction.api.dto;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import com.mycompany.app.transaction.utils.Identifiable;
import com.mycompany.app.transaction.utils.ToStringAllFieldsSupport;

public class DTO extends ToStringAllFieldsSupport implements Identifiable {

	public DTO() {
    }

    public boolean equals(Object other) {
        return EqualsBuilder.reflectionEquals(this, other, false);
    }

    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this, new String[0]);
    }
}
