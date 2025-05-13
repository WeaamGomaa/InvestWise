
package main.java.investWise;
import java.util.*;



public class AccountManager {

    private UserService userService;

    public AccountManager() {
        this.userService = new UserService();
    }


    public boolean verifyOtp(String username,String bankName  ){
        Random random = new Random();
        String key =username;
        int otp = 1000 + random.nextInt(9000);
        Map<String, Integer> otpMap = new HashMap<>();
        otpMap.put(key, otp);
        System.out.println("this is your otp number "+otp +"should be sent by email");
        System.out.print("enter your username :");
        Scanner scanner = new Scanner(System.in);
        String enteredUsername=scanner.next();
        System.out.print("\nenter your otp :");
        int enteredOtp= scanner.nextInt();

        // Verification logic
        if (otpMap.containsKey(enteredUsername)) {
            int storedOtp = otpMap.get(enteredUsername);
            if (storedOtp == enteredOtp) {
                System.out.println("OTP verification successful!");
                return true;
            }
        }

        System.out.println("Invalid OTP or username");
        return false;

    }
    public void linkAccount(String username,String bankName ,String accountNum,String type){
        Account account;
        if (type.equals("bankAccount")){
            account = new Account(accountNum,type,bankName);
            System.out.println("bank account is linked successfully");

        }
        else{
            account = new Account(accountNum,type);
            System.out.println("stock market account is linked successfully");
        }

        User user = userService.getUser(username);
        if (user == null) {
            System.out.println("User not found!");
            return;
        }
        user.getAccounts().add(account);
        System.out.println("Account linked to user: " + username);
    }
}
