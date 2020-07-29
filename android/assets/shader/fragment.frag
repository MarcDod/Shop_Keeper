precision mediump float;
varying vec4 v_color;
varying vec2 v_texCoords;

uniform sampler2D u_texture;
uniform float u_percent;

void main(void ) {
    vec4, myColor = v_color * texture2D(u_texture, v_texCoords);

    float medium = (myColor.r + myColor.g + myColor.b) / 3.0;
    vec4 newColor = vec4(mix(vec3(medium), myColor.rgb, u_percent), myColor.a);

    gl_FragColor = newColor;
}