package elements;

public class Team {

    Javamon team[]; //array of javamon

    public void AddJavamon(Javamon javamon) {
        //add javamon to array
        team[team.length + 1] = javamon;
    }

    public void ReleaseJavamon(int index) {
        //remove javamon from array
        team[index] = null;
    }
    
    public void SwapJavamon(int index1, int index2) {
        //swap javamon in array
        Javamon temp = team[index1];
        team[index1] = team[index2];
        team[index2] = temp;
    }

    public void HealTeam(){
        //heal all javamon in array
        for(int i = 0; i < team.length; i++){
            team[i].Heal();
        }
    }
}
