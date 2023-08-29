import random
items = ["rock", "paper", "scissors"]

win = "Darn you win!!"
lose = "HA HA HA you lose!!"
draw = "Draw? thats crazy, lets go again"rocj
def play(pos1,pos2,pos3):
    if option == pos1:
        print(response)
        if response == pos2 :
            print(win)
        elif response == pos3 :
            print(lose)
        elif response == option:
            print(draw)

while True :
    print("Lets play rock, paper, scissors.""\ntype rock, paper or scissors then click enter ->")
    option = input("type here:")
    response = random.choice(items)

    play(pos1="rock", pos2="scissors", pos3="paper")
    play(pos1="scissors", pos2="paper", pos3="rock")
    play(pos1="paper", pos2="rock", pos3="scissors")

    print("Do you want to play again?")
    play_again = input("Yes or No: ").lower()
    if play_again == "no":
        print("Come again next time")
        break


