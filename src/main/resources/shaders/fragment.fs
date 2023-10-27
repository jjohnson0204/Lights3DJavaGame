#version 400 core

in vec2 textureCoordFragment;
in vec3 fragNormal;
in vec3 fragPos;

out vec4 fragmentColor;

struct Material {
    vec4 ambient;
    vec4 diffuse;
    vec4 specular;
    int hasTexture;
    float reflectance;
};

uniform sampler2D textureSampler;
uniform vec3 ambientLight;
uniform Material material;

vec4 ambientC;
//vec4 diffuseC;
//vec4 specularC;

/*
void setupColors(Material material, vec2 textureCoordinates) {
    if (material.hasTexture == 1) {
        ambientC = texture(textureSampler, textureCoordinates);
        diffuseC = ambientC;
        specularC = ambientC;
    } else {
        ambientC = material.ambient;
        diffuseC = material.diffuse;
        specularC = material.specular;
    }
}
*/

void main() {

    //setupColors(material, textureCoordFragment);
        //fragmentColor = ambientC * vec4(ambientLight, 1);

        if (material.hasTexture == 1) {
            ambientC = texture(textureSampler, textureCoordFragment);
        } else {
            ambientC = material.ambient + material.specular + material.diffuse + material.reflectance;
        }

        fragmentColor = ambientC * vec4(ambientLight, 1);
}