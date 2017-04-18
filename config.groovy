environments {

    development {
        spring.profiles.active = "development"
    }

    test {
        spring.profiles.active = "test"
    }

    production {
        spring.profiles.active = "production"
    }
}