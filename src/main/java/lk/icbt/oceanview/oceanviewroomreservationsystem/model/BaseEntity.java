package lk.icbt.oceanview.oceanviewroomreservationsystem.model;

import java.time.LocalDateTime;
import java.util.Objects;

public abstract class BaseEntity {


    private int id;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;


    public BaseEntity() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    public BaseEntity(int id) {
        this();
        this.id = id;
    }


    public abstract boolean validate();


    public abstract String getDisplayName();


    public void updateTimestamp() {
        this.updatedAt = LocalDateTime.now();
    }


    public boolean isNew() {
        return this.id == 0;
    }


    public int getId() {
        return id;
    }


    public void setId(int id) {
        this.id = id;
    }


    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BaseEntity that = (BaseEntity) o;
        return id == that.id;
    }


    @Override
    public int hashCode() {
        return Objects.hash(id);
    }


    @Override
    public String toString() {
        return this.getClass().getSimpleName() + "{" + "id=" + id + ", createdAt=" + createdAt + ", updatedAt=" + updatedAt + '}';
    }
}
