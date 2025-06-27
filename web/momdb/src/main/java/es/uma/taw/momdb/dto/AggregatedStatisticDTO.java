package es.uma.taw.momdb.dto;

/*
 * @author edugbau (Eduardo González)
 */
public class AggregatedStatisticDTO {
    private String name;
    private Object value;
    private String description;

    public AggregatedStatisticDTO(String name, Object value, String description) {
        this.name = name;
        this.value = value;
        this.description = description;
    }

    // Getters y Setters

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

