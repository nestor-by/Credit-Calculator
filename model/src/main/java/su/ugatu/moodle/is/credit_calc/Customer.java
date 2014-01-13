package su.ugatu.moodle.is.credit_calc;

import com.sun.istack.internal.Nullable;

/**
 * @author rinat.enikeev@gmail.com
 * Date: 13.01.14
 * Time: 18:50
 */
public interface Customer {

    @Nullable Integer getAge();
    @Nullable Sex getSex();
    Customer setSex(Sex sex);
    Customer setAge(Integer age);

}
