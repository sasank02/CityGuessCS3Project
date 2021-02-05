public class City {
    private String name, state, pop;

    public City(String name, String state, String pop) {
        this.name = name;
        this.state = state;
        this.pop = pop;
    }

    @Override
    public String toString() {
        return "name=" + name +
            ", state=" + state +
            ", population=" + pop;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        City city = (City) o;

        if (!name.equals(city.name)) return false;
        if (!state.equals(city.state)) return false;
        return pop.equals(city.pop);
    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + state.hashCode();
        result = 31 * result + pop.hashCode();
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

    public String getPop() {
        return pop;
    }

    public void setPop(String pop) {
        this.pop = pop;
    }
}
