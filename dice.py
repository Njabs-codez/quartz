import random
# faces = random.randint(1,6)
print("Want to test your luck? ")
yes_roll = input("yes or no? ").lower()

while yes_roll == "yes":
    print("Roll the dice and see if you can get a six""\nTo roll die, type roll:")
    play_roll = input().lower()

    if play_roll == "roll":
        face = random.randint(1, 6)
        print(face)
    if face == 6:
        print('you got a 6 you are lucky')
    elif face != 6:
        print('you got a '+str(face))
        print("roll again? ")
        stop_roll = input('yes or no? ').lower()
        if stop_roll == "no":
            break





