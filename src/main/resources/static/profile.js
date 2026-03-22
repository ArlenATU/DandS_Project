const userId = 1;

let profileData = {};

window.onload = loadProfile;

function loadProfile(){

    fetch(`/api/profile/${userId}`)
        .then(res=>res.json())
        .then(data=>{

            profileData = data.profile;

            document.getElementById("username").innerText = profileData.name;
            document.getElementById("nameText").innerText = profileData.name;
            document.getElementById("bioText").innerText = profileData.bio;
            document.getElementById("avatarPreview").src = profileData.profilePictureUrl || "https://i.pravatar.cc/100";
            document.getElementById("miniAvatar").src = profileData.profilePictureUrl || "https://i.pravatar.cc/100";

            renderStories(data.stories);

        });
}

function renderStories(stories){

    const container = document.getElementById("storiesContainer");
    container.innerHTML = "";

    if(stories.length === 0){
        container.innerHTML = "<p>No stories yet</p>";
        return;
    }

    stories.forEach(story => {

        const div = document.createElement("div");
        div.classList.add("story");

        div.innerHTML = `
        <h4>${story.title}</h4>
        <p>${story.content}</p>
    `;

        container.appendChild(div);

    });
}

function editName(){

let value = prompt("Enter new name:", profileData.name);

if(value){
profileData.name = value;
document.getElementById("nameText").innerText = value;
document.getElementById("username").innerText = value;
}

}

function editBio(){

let value = prompt("Enter new bio:", profileData.bio);

if(value){
profileData.bio = value;
document.getElementById("bioText").innerText = value;
}

}

function uploadAvatar(){

document.getElementById("avatarInput").click();

}

document.getElementById("avatarInput").addEventListener("change", function(){

const file = this.files[0];

if(!file) return;

const reader = new FileReader();

reader.onload = function(e){

profileData.avatarUrl = e.target.result;

document.getElementById("avatarPreview").src = e.target.result;
document.getElementById("miniAvatar").src = e.target.result;

};

reader.readAsDataURL(file);

});

function saveProfile(){

    fetch(`/api/profile/${userId}`,{
        method:"PUT",
        headers:{
            "Content-Type":"application/json"
        },
        body:JSON.stringify(profileData)
    })
        .then(()=>{

            alert("Profile updated");
            location.reload();

        });
}