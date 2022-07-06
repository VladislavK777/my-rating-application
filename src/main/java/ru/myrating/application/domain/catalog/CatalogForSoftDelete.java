package ru.myrating.application.domain.catalog;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler"}, ignoreUnknown = true)
public interface CatalogForSoftDelete extends Serializable {
}
