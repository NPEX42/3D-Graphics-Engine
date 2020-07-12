#version 330 core
in vec2 v_UV;
out vec4 o_PixelColor;
uniform sampler2D t_Albedo;
void main() {
	o_PixelColor = texture(t_Albedo, v_UV);//RED
}