import java.util.InputMismatchException;
import java.util.Locale;
import java.util.Objects;
import java.util.Scanner;

public class Main {
    private static Scanner scanner;

    public static void main(String[] args) {

        String a;
        Scanner sc = new Scanner(System.in);
        System.out.println("Введите число, которое хотели бы перевести");
        a = sc.next();
        String cel, plus_m;
        System.out.println("Какое число необходимо перевести? (Целое/Вещественное)");
        cel = sc.next();
        System.out.println("Какое число необходимо перевести? (Отрицательное/Положительное)");
        plus_m = sc.next();
        System.out.println("Число " + a + " в двоичной системе счисления = " + what_is_num(a, cel, plus_m));
    }

    public static StringBuilder what_is_num(String a, String cel, String plus_m) {
        StringBuilder ans_str;
        ans_str = new StringBuilder("");

        boolean is_cel = (Objects.equals(cel, "Целое")) | (Objects.equals(cel, "целое"));
        boolean is_drob = (Objects.equals(cel, "Вещественное")) | (Objects.equals(cel, "вещественное"));

        if((Objects.equals(plus_m, "Положительное")) | (Objects.equals(plus_m, "положительное"))){
            if(is_cel){
                ans_str = beaut_ans(to_two_cel(a));
            } else if(is_drob){
                ans_str = not_cel_two(a, "YES");
            } else{
                System.err.println("Ваш ответ \"" + cel + "\" некорректен");
                System.exit(0);
            }
        } else if ((Objects.equals(plus_m, "Отрицательное")) | (Objects.equals(plus_m, "отрицательное"))){
            if(is_cel){
                ans_str = beaut_ans(to_two_minuse(a));
            } else if(is_drob){
                ans_str = not_cel_two(a.substring(1), "NO");
            } else{
                System.err.println("Ваш ответ \"" + cel + "\" некорректен");
                System.exit(0);
            }
        } else {
            System.err.println("Ваш ответ \"" + plus_m + "\" некорректен");
            System.exit(0);
        }
        return ans_str;
    }

    public static StringBuilder to_two_cel(String a) {
        StringBuilder str_ost;
        str_ost = new StringBuilder();
        int num;
        try {
            num = Integer.parseInt(String.valueOf(a));
        }
        catch (Exception ex){
            System.err.println("Ваше значение \"" + a + "\" некорректно");
            System.exit(0);
        }

        num = Integer.parseInt(String.valueOf(a));
        while (num != 1) {
            str_ost.append(String.valueOf(num % 2));
            num /= 2;
        }
        str_ost.append("1");
        while (str_ost.length() < 8){
            str_ost.append(0);
        }
        return str_ost.reverse();
    }

    public static int num_ten(StringBuilder str_) {
        str_.reverse();
        int num_des, num_;
        String l;
        num_des = 0;
        for (int i = 0; i < str_.length(); i++) {
            l = String.valueOf(str_.charAt(i));
            num_ = Integer.parseInt(l);
            num_des += num_ * Math.pow(2, i);
        }
        return num_des;
    }

    public static StringBuilder to_two_minuse(String a) {
        StringBuilder str_ost, ans_str;
        String res;
        int num;
        ans_str = new StringBuilder();
        a = a.substring(1);
        str_ost = to_two_cel(a);

        str_ost.reverse();
        while (str_ost.length() < 8) {
            str_ost.append("0");
        }
        str_ost.reverse();

        for (int i = 0; i < str_ost.length(); i++) {
            if (str_ost.charAt(i) == '0') {
                ans_str.append('1');
            } else {
                ans_str.append('0');
            }
        }
        num = num_ten(ans_str);
        num += 1;
        res = String.valueOf(num);
        return to_two_cel(res);
    }

    public static StringBuilder not_cel_two(String num, String is_plus){
        int cel_part, num_of_poryadok, znak;
        float num_ = 0, drob_part;
        StringBuilder ans, poryadok, need_str;
        ans = new StringBuilder("");
        need_str = new StringBuilder("");


        try {
            num_ = Float.parseFloat(num);
        }
        catch (Exception ex1){
            try {
                int id_el = num.indexOf(',');
                num = num.substring(0, id_el) + "." + num.substring(id_el+1);
                num_ = Float.parseFloat(num);
            } catch (Exception ex2){
                System.err.println("Ваш ответ \"" + num + "\" некорректен");
                System.exit(0);
            }
        }

        if((num_ < 1) && (num_ > 0)){
            return with_zerro_drob(num_);
        } else{
            cel_part = (int) num_; // получили целую часть
            drob_part = num_ - cel_part;
            need_str.append(to_two_cel(String.valueOf(cel_part)));
            need_str.append(with_zerro_drob(drob_part));

            num_of_poryadok = to_two_cel(String.valueOf(cel_part)).length() - 1;
            poryadok = to_two_cel(String.valueOf(127+num_of_poryadok));

            if(Objects.equals(is_plus, "NO")){
                znak = 1;
            } else {
                znak = 0;
            }

            ans.append(znak);
            ans.append(poryadok);
            while (need_str.length() < 23){
                need_str.append(0);
            }
            ans.append(need_str.substring(1));
            return ans;
        }
    }

    public static StringBuilder with_zerro_drob (float a) {
        StringBuilder drob_in_two, just;
        just = new StringBuilder("0.");
        float drob_part;
        drob_part = a;
        drob_in_two = new StringBuilder("");
        while (drob_in_two.length() != 3){
            drob_part *= 2.0;
            drob_in_two.append((int) Math.floor(drob_part));
            if(drob_part > 1.0){
                drob_part -= 1.0;
            }
        }
        return just.append(drob_in_two);
    }

    public static StringBuilder beaut_ans (StringBuilder final_number){
        String type;
        Scanner sc = new Scanner(System.in);
        System.out.println("Какого типа необходимо вывести результат? byte/short/int/long");
        type = sc.next();

        int len = 0;
        switch (type){
            case "byte":
                len = 8;
                break;
            case "short":
                len = 16;
                break;
            case "int":
                len = 32;
                break;
            case "long":
                len = 64;
                break;
            default:
                System.err.println("Введенный тип \"" + type + "\" некорректен");
                System.exit(0);
        }
        final_number.reverse();
        while (final_number.length() < len){
            final_number.append(0);
        }
        final_number.reverse();
        if(final_number.length() > len){
            final_number.substring(len);
        }
        return final_number;
    }

        private static void scanner_init(Locale locale) {
            scanner.useLocale(locale);
        }
}