public class City {
    private String name, state, zip;

    public City(String name, String state, String zip) {
        this.name = name;
        this.state = state;
        this.zip = zip;
    }

    @Override
    public String toString() {
        return "name=" + name +
            ", state=" + state +
            ", zip=" + zip;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        City city = (City) o;

        if (!name.equals(city.name)) return false;
        if (!state.equals(city.state)) return false;
        return zip.equals(city.zip);
    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + state.hashCode();
        result = 31 * result + zip.hashCode();
        return result;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }
}
