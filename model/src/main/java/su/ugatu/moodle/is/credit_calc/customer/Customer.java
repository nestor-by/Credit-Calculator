package su.ugatu.moodle.is.credit_calc.customer;

/**
 * @author rinat.enikeev@gmail.com
 * Date: 13.01.14
 * Time: 18:50
 */
public interface Customer {

    Integer getAge();

    Sex getSex();

    Customer setSex(Sex sex);

    Customer setAge(Integer age);

}
