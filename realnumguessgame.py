import random
print("You get 3 tries, guess my number from within a range you chose")
print("Sounds easy right? you could possibly lose to a simple code like me :)")
a = int(input("insert the lowest number in your range that i can choose from: "))
b = int(input("insert the highest number in your range that i can choose from: "))
computer = random.randint(a, b)
win = "That is correct"
high = "that is too high, try again"
low = "that is too low, try again"
print("Can you guess my number?")
for turn in range(3):
    player = int(input())
    if player > computer:
        print(high)
        continue
    elif player < computer:
        print(low)
        continue
    else:
        print(win)
        print("Congratulations, you've guessed within your turn. LUCK is all that is")
        break
else:
    print("couldn't guess within your turns. You lose, beaten by a simple program")
