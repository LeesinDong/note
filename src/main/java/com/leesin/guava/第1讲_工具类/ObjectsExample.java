package com.leesin.guava.第1讲_工具类;


import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import com.google.common.collect.ComparisonChain;
import java.util.Calendar;


/***************************************
 * @author:Alex Wang
 * @Date:2017/10/7
 * @QQ: 532500648
 ***************************************/
public class ObjectsExample {
    // cr 本质：主要为了生成hashCode equal方法, jdk7有与之对应的两个放阿飞
    //  就连个方法 hashCoe 和equals

    public static void main(String[] args) {
        final Guava guava = new Guava("Google", "23.0", Calendar.getInstance());
        System.out.println(guava);
        System.out.println(guava.hashCode());

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.YEAR, 1);
        final Guava guava2 = new Guava("Google", "23.0", calendar);
        System.out.println(guava.compareTo(guava2));
    }

    static class Guava implements Comparable<Guava> {
        private final String manufacturer;
        private final String version;
        private final Calendar releaseDate;

        public Guava(String manufacturer, String version, Calendar releaseDate) {
            this.manufacturer = manufacturer;
            this.version = version;
            this.releaseDate = releaseDate;
        }

        /**
         * cr MoreObjects
         */
        @Override
        public String toString() {
            return MoreObjects.toStringHelper(this).omitNullValues()
                    .add("manufacturer", this.manufacturer)
                    .add("version", this.version)
                    .add("releaseDate", this.releaseDate).toString();
        }

        @Override
        public int hashCode() {
            return Objects.hashCode(manufacturer, version, releaseDate);
        }

        /**
         * cr Objects.equals 变成 Objects.equal
         */
        @Override
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || getClass() != obj.getClass()) {
                return false;
            }

            Guava guava = (Guava) obj;

            // 等价于jdk的Objects.equals 防止空指针
            return Objects.equal(this.manufacturer, guava.manufacturer)
                    && Objects.equal(this.version, guava.version)
                    && Objects.equal(this.releaseDate, guava.releaseDate);
        }

        @Override
        public int compareTo(Guava o) {
            return ComparisonChain.start().compare(this.manufacturer, o.manufacturer)
                    .compare(this.version, o.version)
                    .compare(this.releaseDate, o.releaseDate).result();
        }



        /*@Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Guava guava = (Guava) o;

            if (manufacturer != null ? !manufacturer.equals(guava.manufacturer) : guava.manufacturer != null)
                return false;
            if (version != null ? !version.equals(guava.version) : guava.version != null) return false;
            return releaseDate != null ? releaseDate.equals(guava.releaseDate) : guava.releaseDate == null;
        }

        @Override
        public int hashCode() {
            int result = manufacturer != null ? manufacturer.hashCode() : 0;
            result = 31 * result + (version != null ? version.hashCode() : 0);
            result = 31 * result + (releaseDate != null ? releaseDate.hashCode() : 0);
            return result;
        }

        @Override
        public String toString() {
            return "Guava{" +
                    "manufacturer='" + manufacturer + '\'' +
                    ", version='" + version + '\'' +
                    ", releaseDate=" + releaseDate +
                    '}';
        }*/
    }
}
