package edu.thanglong.domain.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "seat_categories")
public class SeatCategory {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "category_name", length = 50)
    private String categoryName;
    
    @Column(precision = 10, scale = 2)
    private BigDecimal price;
    
    @Column(length = 255)
    private String description;

    public SeatCategory() {}

    public SeatCategory(Long id, String categoryName, BigDecimal price, String description) {
        this.id = id;
        this.categoryName = categoryName;
        this.price = price;
        this.description = description;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getCategoryName() { return categoryName; }
    public void setCategoryName(String categoryName) { this.categoryName = categoryName; }

    public BigDecimal getPrice() { return price; }
    public void setPrice(BigDecimal price) { this.price = price; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public static SeatCategoryBuilder builder() { return new SeatCategoryBuilder(); }

    public static class SeatCategoryBuilder {
        private Long id;
        private String categoryName;
        private BigDecimal price;
        private String description;

        public SeatCategoryBuilder id(Long id) { this.id = id; return this; }
        public SeatCategoryBuilder categoryName(String categoryName) { this.categoryName = categoryName; return this; }
        public SeatCategoryBuilder price(BigDecimal price) { this.price = price; return this; }
        public SeatCategoryBuilder description(String description) { this.description = description; return this; }

        public SeatCategory build() {
            return new SeatCategory(id, categoryName, price, description);
        }
    }
}
