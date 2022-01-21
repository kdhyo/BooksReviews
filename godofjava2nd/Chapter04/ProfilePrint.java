public class ProfilePrint {
    private byte age;
    private String name;
    private boolean isMarried;

    public static void main(String[] args) {
        ProfilePrint profilePrint = new ProfilePrint();
        profilePrint.setAge((byte) 25);
        profilePrint.setName("kimDongHyo");
        profilePrint.setMarried(false);
        System.out.println(profilePrint.getAge());
        System.out.println(profilePrint.getName());
        System.out.println(profilePrint.getMarrid());
    }

    public void setAge(byte age) {
        this.age = age;
    }

    public byte getAge() {
        return age;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setMarried(boolean flag) {
        this.isMarried = flag;
    }

    public boolean getMarrid() {
        return isMarried;
    }


}