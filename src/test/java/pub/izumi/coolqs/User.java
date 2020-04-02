package pub.izumi.coolqs;

import lombok.Data;

@Data
public class User {
    private int id;
    private String name;
    private String phone;
    private String sex;
    private String IDCard;

    public static class Builder {
        private int id;
        private String name;
        private String phone;
        private String sex;
        private String IDCard;

        public Builder(int id, String name) {
            this.id = id;
            this.name = name;
        }
        public Builder phone(String val) {
            this.phone = val;
            return this;
        }
        public Builder sex(String val) {
            this.sex = val;
            return this;
        }
        public Builder IDCard(String val) {
            this.IDCard = val;
            return this;
        }
        public User build() {
            return new User(this);
        }
    }

    private User(Builder builder) {
        id = builder.id;
        name = builder.name;
        phone = builder.phone;
        sex = builder.sex;
        IDCard = builder.IDCard;
    }

    public User(int id, String name) {
        this.id = id;
        this.name = name;
    }
}
