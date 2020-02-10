package by.koshko.crimes.entity;

import java.time.LocalDateTime;

public class StopAndSearch {

    private long id;
    private String type;
    private boolean involved_person;
    private LocalDateTime timestamp;
    private String operation_name;
    private Location location;
    private String gender;
    private String age_range;
    private String self_defined_ethnicity;
    private String officer_defined_ethnicity;
    private String legislation;
    private String object_of_search;
    private String outcome;
    private OutcomeObject outcome_object;
    private Boolean outcome_linked_to_object_of_search;
    private Boolean removal_of_more_than_outer_clothing;

    public long getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public boolean isInvolved_person() {
        return involved_person;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public String getOperation_name() {
        return operation_name;
    }

    public Location getLocation() {
        return location;
    }

    public String getGender() {
        return gender;
    }

    public String getAge_range() {
        return age_range;
    }

    public String getSelf_defined_ethnicity() {
        return self_defined_ethnicity;
    }

    public String getOfficer_defined_ethnicity() {
        return officer_defined_ethnicity;
    }

    public String getLegislation() {
        return legislation;
    }

    public String getObject_of_search() {
        return object_of_search;
    }

    public String getOutcome() {
        return outcome;
    }

    public OutcomeObject getOutcome_object() {
        return outcome_object;
    }

    public Boolean getOutcome_linked_to_object_of_search() {
        return outcome_linked_to_object_of_search;
    }

    public Boolean getRemoval_of_more_than_outer_clothing() {
        return removal_of_more_than_outer_clothing;
    }

    public StopAndSearch(StopAndSearchBuilder builder) {
        id = builder.id;
        type = builder.type;
        involved_person = builder.involved_person;
        timestamp = builder.timestamp;
        operation_name = builder.operation_name;
        location = builder.location;
        gender = builder.gender;
        age_range = builder.age_range;
        self_defined_ethnicity = builder.self_defined_ethnicity;
        officer_defined_ethnicity = builder.officer_defined_ethnicity;
        legislation = builder.legislation;
        object_of_search = builder.object_of_search;
        outcome = builder.outcome;
        outcome_object = builder.outcome_object;
        outcome_linked_to_object_of_search = builder.outcome_linked_to_object_of_search;
        removal_of_more_than_outer_clothing = builder.removal_of_more_than_outer_clothing;
    }

    public static class StopAndSearchBuilder {

        private long id;
        private String type;
        private boolean involved_person;
        private LocalDateTime timestamp;
        private String operation_name;
        private Location location;
        private String gender;
        private String age_range;
        private String self_defined_ethnicity;
        private String officer_defined_ethnicity;
        private String legislation;
        private String object_of_search;
        private String outcome;
        private OutcomeObject outcome_object;
        private Boolean outcome_linked_to_object_of_search;
        private Boolean removal_of_more_than_outer_clothing;

        public StopAndSearchBuilder setId(long id) {
            this.id = id;
            return this;
        }

        public StopAndSearchBuilder setType(String type) {
            this.type = type;
            return this;
        }

        public StopAndSearchBuilder setInvolved_person(boolean involved_person) {
            this.involved_person = involved_person;
            return this;
        }

        public StopAndSearchBuilder setTimestamp(LocalDateTime timestamp) {
            this.timestamp = timestamp;
            return this;
        }

        public StopAndSearchBuilder setOperation_name(String operation_name) {
            this.operation_name = operation_name;
            return this;
        }

        public StopAndSearchBuilder setLocation(Location location) {
            this.location = location;
            return this;
        }

        public StopAndSearchBuilder setGender(String gender) {
            this.gender = gender;
            return this;
        }

        public StopAndSearchBuilder setAge_range(String age_range) {
            this.age_range = age_range;
            return this;
        }

        public StopAndSearchBuilder setSelf_defined_ethnicity(String self_defined_ethnicity) {
            this.self_defined_ethnicity = self_defined_ethnicity;
            return this;
        }

        public StopAndSearchBuilder setOfficer_defined_ethnicity(String officer_defined_ethnicity) {
            this.officer_defined_ethnicity = officer_defined_ethnicity;
            return this;
        }

        public StopAndSearchBuilder setLegislation(String legislation) {
            this.legislation = legislation;
            return this;
        }

        public StopAndSearchBuilder setObject_of_search(String object_of_search) {
            this.object_of_search = object_of_search;
            return this;
        }

        public StopAndSearchBuilder setOutcome(String outcome) {
            this.outcome = outcome;
            return this;
        }

        public StopAndSearchBuilder setOutcome_object(OutcomeObject outcome_object) {
            this.outcome_object = outcome_object;
            return this;
        }

        public StopAndSearchBuilder setOutcome_linked_to_object_of_search(Boolean outcome_linked_to_object_of_search) {
            this.outcome_linked_to_object_of_search = outcome_linked_to_object_of_search;
            return this;
        }

        public StopAndSearchBuilder setRemoval_of_more_than_outer_clothing(Boolean removal_of_more_than_outer_clothing) {
            this.removal_of_more_than_outer_clothing = removal_of_more_than_outer_clothing;
            return this;
        }

        public StopAndSearch build() {
            return new StopAndSearch(this);
        }
    }
}
