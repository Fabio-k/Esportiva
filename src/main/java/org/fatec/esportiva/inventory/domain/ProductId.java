package org.fatec.esportiva.inventory.domain;

import org.springframework.util.Assert;

import java.util.UUID;

public record ProductId(UUID id) {
   public ProductId {
       Assert.notNull(id, "id must not be null");
   }

   public ProductId(){
       this(UUID.randomUUID());
   }

}
