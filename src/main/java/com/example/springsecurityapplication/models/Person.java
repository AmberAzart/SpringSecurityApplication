package com.example.springsecurityapplication.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import java.util.List;
import java.util.Objects;

@Entity
@Table(name="Person")
public class Person {
    @Id
    @Column(name="id") // коннектимся к сущ. полю (БД и таблицу создали раньше, чем модель)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotEmpty(message="Логин не может быть пустым")
    @Size(min=5, max=100, message="Логин должен содержать от 5 до 100 символов")
    @Column(name="login")
    private String login;

    @NotEmpty(message = "Пароль не может быть пустым")
    @Column(name="password")
    //На пароль ещё требуется добавить @Pattern, чтобы не было легких паролей
    private String password;
    @Column(name="role")
    private String role;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    //Для работы с корзиной
    //@JoinTable указывает, что для реализации связи М-М создаётся промежуточная таблица product_cart
    //@JoinColumn указывает, какие колонки будут в промежуточной таблице. Первой указывается колонка, имеющая отношение к текущему классу("product_id")
    @ManyToMany()
    @JoinTable(name="product_cart", joinColumns = @JoinColumn(name = "person_id"), inverseJoinColumns = @JoinColumn(name = "product_id"))
    private List<Product> productList;

    @OneToMany (mappedBy = "person", fetch = FetchType.EAGER)
    private List<Order> orderList;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return id == person.id && Objects.equals(login, person.login) && Objects.equals(password, person.password);
    }

    //Данный метод преобразует объект класса в число. Обратно преобразовать практически невозможно
    @Override
    public int hashCode() {
        return Objects.hash(id, login, password);
    }
}
